const admin = require('./node_modules/firebase-admin');
const serviceAccount = require("./serviceAccountKey1.json");

const collectionKey1 = "Userdet"; //name of the collection

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  //databaseURL: "https://march2019-ac598.firebaseio.com"
  databaseURL: "https://betforparty.firebaseio.com"
});

const firestore = admin.firestore();

firestore.collection(collectionKey1).get()
 .then((snapshot) => {
	snapshot.forEach((doc) => {
		var collectionKey = doc.id;
		firestore.collection(collectionKey).get()
 .then((snapshot) => {
	snapshot.forEach((doc) => {
		var value1 = +doc.data().mcnt
		var value2 = +doc.data().winner
		if(isNaN(value1)){
			console.log("Amount error in ",collectionKey,doc.id,doc.data().mcnt)
		}
		if(!isNaN(value2)){
			console.log("Mcnt error in ",collectionKey,doc.id,doc.data().mcnt)
		}
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});