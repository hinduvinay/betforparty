const admin = require('./node_modules/firebase-admin');
const serviceAccount = require("./serviceAccountKey1.json");//betforparty
//const serviceAccount = require("./serviceAccountKey.json");//march2019
const collectionKey1 = "Userdet"; //name of the collection

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://betforparty.firebaseio.com"
  //databaseURL: "https://march2019-ac598.firebaseio.com"
});

const firestore = admin.firestore();

firestore.collection(collectionKey1).get()
 .then((snapshot) => {
	snapshot.forEach((doc) => {
		var collectionKey = doc.id;
		firestore.collection(collectionKey).get()
.then((snapshot) => {
	snapshot.forEach((doc) => {
		if(doc.data().pred == ""){
			firestore.collection(collectionKey).doc(doc.id).update({pred:"-"});
		}
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});