import http from 'k6/http';

import { fail } from 'k6';
import { SharedArray } from "k6/data";
import { randomItem, randomIntBetween } from "https://jslib.k6.io/k6-utils/1.1.0/index.js";

export let options = {
    vus: 100,
    duration: '60s',
};

// not using SharedArray here will mean that the code in the function call (that is what loads and
// parses the json) will be executed per each VU which also means that there will be a complete copy
// per each VU
const data = new SharedArray("product names", function() { return JSON.parse(open('./product-names.json')); });

export default function () {
    var product = randomItem(data);
    // var product = data[Math.floor(Math.random() * data.length)]

    // fetch product list
    var resp = http.get('http://localhost:8080/inventory/item');

    if (resp.status !== 200) {
        fail('failed to fetch inventory list');
    }

    let item = JSON.parse(resp.body).find(i => i.name === product);

    // item does not exist
    if (!item) {
        // create inventory item
        resp = http.post('http://localhost:8080/inventory/item?name='+encodeURI(product));
        if (resp.status !== 200) {
            fail('failed to create inventory item');
        }
    } else {
        resp = http.get('http://localhost:8080/inventory/item/' + item.id);
        if (resp.status !== 200) {
            fail('failed to fetch inventory item');
        }

        var currentCount = JSON.parse(resp.body).currentCount;
        var stockOperation;
        // less than 10 items?
        if (currentCount < 1) {
            // increase stock
            currentCount = randomIntBetween(1, 20);
            stockOperation = 'checkin';
        } else {
            // remove items
            currentCount = randomIntBetween(1, currentCount);
            stockOperation = 'remove';
        }
        var url = 'http://localhost:8080/inventory/item/' + item.id + '/' + stockOperation + '?version=1&count=' + currentCount;
        resp = http.post(url);
        if (resp.status === 409) {
            console.log('Conflict');
        } else if (resp.status !== 200) {
            console.log('url: ', url, 'status', resp.status, 'body', resp.body);
            fail('failed to change item stock quantity');
        }
    }
}