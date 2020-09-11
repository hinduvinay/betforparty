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
		if(doc.data().winner == "-"){
			firestore.collection(collectionKey).doc(doc.id).update({amt:0});
		}else if(doc.data().pred == doc.data().winner){
			firestore.collection(collectionKey).doc(doc.id).update({amt:0});
		}else if(doc.data().pred == "-" || doc.data().pred == ""){
			firestore.collection(collectionKey).doc(doc.id).update({amt:20});
			firestore.collection(collectionKey).doc(doc.id).update({pred:"-"});
		}else {
			firestore.collection(collectionKey).doc(doc.id).update({amt:10});
		}
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});