const admin = require('./node_modules/firebase-admin');
const serviceAccount = require("./serviceAccountKey1.json");//betforparty
//const serviceAccount = require("./serviceAccountKey.json");//march2019
const collectionKey1 = "Userdet"; //name of the collection
const mcnt = "60"

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
		firestore1.collection(collectionKey).doc(mcnt).get().then((doc1)=>{
			if(doc1.data().pred == doc1.data().winner){
			firestore1.collection(collectionKey).doc(mcnt).update({amt:0});
			}else if(doc1.data().pred == "-" || doc1.data().pred == ""){
			firestore1.collection(collectionKey).doc(mcnt).update({amt:200});
			firestore1.collection(collectionKey).doc(mcnt).update({pred:"-"});
			}else {
			firestore1.collection(collectionKey).doc(mcnt).update({amt:100});
			}
		}).catch((err)=>{
			console.error("error",err);
		});
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});