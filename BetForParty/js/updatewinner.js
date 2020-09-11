const admin = require('./node_modules/firebase-admin');
//const serviceAccount = require("./serviceAccountKey.json");//march2019
const serviceAccount = require("./serviceAccountKey1.json");//betforparty
const collectionKey1 = "Userdet"; //name of the collection
const mcnt = "60"
const winteam = "MI"
const date = "2019-05-12"
const team1 = "MI"
const team2 = "CSK"

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  //databaseURL: "https://march2019-ac598.firebaseio.com //march2019
  databaseURL: "https://betforparty.firebaseio.com"//betforparty
});

const firestore1 = admin.firestore();

firestore1.collection(collectionKey1).get()
 .then((snapshot) => {
	snapshot.forEach((doc) => {
		var collectionKey = doc.id;
		firestore1.collection(collectionKey).doc(mcnt).get().then((doc1)=>{
			if(!doc1.exists){
				firestore1.collection(collectionKey).doc(mcnt).set({
				winner: winteam,
				date: date,
				mcnt: 60,
				pred: "-",
				team1: team1,
				team2: team2,
				amt: 0
				});
				console.log("Added document for ", collectionKey);
			}else{
				firestore1.collection(collectionKey).doc(mcnt).update({winner: winteam});
			}
		}).catch((err)=>{
			console.error("error");
		});
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});