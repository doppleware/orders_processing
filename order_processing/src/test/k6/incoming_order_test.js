import { check } from "k6";
// Or individual classes and constants
import {
    Writer,
    Reader,
    Connection,
    SchemaRegistry,
    SCHEMA_TYPE_STRING,
    CODEC_SNAPPY,
    SCHEMA_TYPE_JSON,
      TOPIC_NAME_STRATEGY,
        KEY,
        VALUE


} from "k6/x/kafka";

import { randomString } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

const topic = "incomingOrders";

const writer = new Writer({
    // WriterConfig object
    brokers: ["localhost:9092"],
    topic: "incomingOrders",
    autoCreateTopic: true,
    compression: CODEC_SNAPPY,
});

const schemaRegistry = new SchemaRegistry({
    url:"http://localhost:8081" });


export default function () {
   const message = {
       key: "test",
       value: "data"
   };
   const jsonString = JSON.stringify(message);

   const messageKey="key"

   writer.produce({
       // ProduceConfig object
       messages: [
           // Message object(s)
           {
           key: schemaRegistry.serialize({
               data: "key",
               schemaType: SCHEMA_TYPE_STRING,
           }),
           value: schemaRegistry.serialize({
                                             data: {
                                               key: randomString(8),
                                               value: randomString(8),

                                             },
                                             schemaType: SCHEMA_TYPE_JSON}),
           },
       ],
       });
}

