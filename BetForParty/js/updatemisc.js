const admin = require('./node_modules/firebase-admin');
const serviceAccount = require("./serviceAccountKey1.json");//betforparty
//const serviceAccount = require("./serviceAccountKey.json");//march2019
const collectionKey1 = "Userdet"; //name of the collection
const mcnt = "57"

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  //databaseURL: "https://march2019-ac598.firebaseio.com"
  databaseURL: "https://betforparty.firebaseio.com"
});

const firestore1 = admin.firestore();

firestore1.collection(collectionKey1).get()
 .then((snapshot) => {
	snapshot.forEach((doc) => {
		var collectionKey = doc.id;
		firestore1.collection(collectionKey).doc(mcnt).update({mcnt: 56});
			});
	}).catch((err2) => {
   console.error("Error reading Collection err2: ", err2);
});