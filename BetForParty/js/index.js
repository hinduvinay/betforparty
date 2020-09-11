const admin = require('./node_modules/firebase-admin');
const serviceAccount = require("./serviceAccountKey1.json");

const data = require("./data/Anantharam02.json");
const collectionKey = "suresh.thota@softvision.com"; //name of the collection

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  //databaseURL: "https://march2019-ac598.firebaseio.com"
  databaseURL: "https://betforparty.firebaseio.com"
});

const firestore = admin.firestore();
const settings = {timestampsInSnapshots: true};

firestore.settings(settings);
if (data && (typeof data === "object")) {
Object.keys(data).forEach(docKey => {
 firestore.collection(collectionKey).doc(docKey).set(data[docKey]).then((res) => {
    console.log("Document " + docKey + " successfully written!");
}).catch((error) => {
   console.error("Error writing document: ", error);
});
});
}