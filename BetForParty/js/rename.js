const admin = require('./node_modules/firebase-admin');
const serviceAccount = require("./serviceAccountKey.json");

const collectionKey1 = "Userdet"; //name of the collection

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
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
		//************TeamB*************************************
		if(doc.data().TeamA == 'Chennai Super Kings'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamA:"CSK"});
		}
		if(doc.data().TeamA == 'Delhi Capitals'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamA:"DC"});
		}
		if(doc.data().TeamA == 'Kings XI Punjab'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamA:"KXIP"});
		}
		if(doc.data().TeamA == 'Kolkata Knight Riders'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamA:"KKR"});
		}
		if(doc.data().TeamA == 'Mumbai Indians'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamA:"MI"});
		}
		if(doc.data().TeamA == 'Rajasthan Royals'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamA:"RR"});
		}
		if(doc.data().TeamA == 'Royal Challengers Bangalore'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamA:"RCB"});
		}
		if(doc.data().TeamA == 'Sunrisers Hyderabad'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamA:"SRH"});
		}
		
		//************TeamB*************************************
		if(doc.data().TeamB == 'Chennai Super Kings'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamB:"CSK"});
		}
		if(doc.data().TeamB == 'Delhi Capitals'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamB:"DC"});
		}
		if(doc.data().TeamB == 'Kings XI Punjab'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamB:"KXIP"});
		}
		if(doc.data().TeamB == 'Kolkata Knight Riders'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamB:"KKR"});
		}
		if(doc.data().TeamB == 'Mumbai Indians'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamB:"MI"});
		}
		if(doc.data().TeamB == 'Rajasthan Royals'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamB:"RR"});
		}
		if(doc.data().TeamB == 'Royal Challengers Bangalore'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamB:"RCB"});
		}
		if(doc.data().TeamB == 'Sunrisers Hyderabad'){
			firestore.collection(collectionKey).doc(doc.id).update({TeamB:"SRH"});
		}
		
		//************Pred*************************************
		if(doc.data().Pred == 'Chennai Super Kings'){
			firestore.collection(collectionKey).doc(doc.id).update({Pred:"CSK"});
		}
		if(doc.data().Pred == 'Delhi Capitals'){
			firestore.collection(collectionKey).doc(doc.id).update({Pred:"DC"});
		}
		if(doc.data().Pred == 'Kings XI Punjab'){
			firestore.collection(collectionKey).doc(doc.id).update({Pred:"KXIP"});
		}
		if(doc.data().Pred == 'Kolkata Knight Riders'){
			firestore.collection(collectionKey).doc(doc.id).update({Pred:"KKR"});
		}
		if(doc.data().Pred == 'Mumbai Indians'){
			firestore.collection(collectionKey).doc(doc.id).update({Pred:"MI"});
		}
		if(doc.data().Pred == 'Rajasthan Royals'){
			firestore.collection(collectionKey).doc(doc.id).update({Pred:"RR"});
		}
		if(doc.data().Pred == 'Royal Challengers Bangalore'){
			firestore.collection(collectionKey).doc(doc.id).update({Pred:"RCB"});
		}
		if(doc.data().Pred == 'Sunrisers Hyderabad'){
			firestore.collection(collectionKey).doc(doc.id).update({Pred:"SRH"});
		}		
		
		//************Winner*************************************
		if(doc.data().Winner == 'Chennai Super Kings'){
			firestore.collection(collectionKey).doc(doc.id).update({Winner:"CSK"});
		}
		if(doc.data().Winner == 'Delhi Capitals'){
			firestore.collection(collectionKey).doc(doc.id).update({Winner:"DC"});
		}
		if(doc.data().Winner == 'Kings XI Punjab'){
			firestore.collection(collectionKey).doc(doc.id).update({Winner:"KXIP"});
		}
		if(doc.data().Winner == 'Kolkata Knight Riders'){
			firestore.collection(collectionKey).doc(doc.id).update({Winner:"KKR"});
		}
		if(doc.data().Winner == 'Mumbai Indians'){
			firestore.collection(collectionKey).doc(doc.id).update({Winner:"MI"});
		}
		if(doc.data().Winner == 'Rajasthan Royals'){
			firestore.collection(collectionKey).doc(doc.id).update({Winner:"RR"});
		}
		if(doc.data().Winner == 'Royal Challengers Bangalore'){
			firestore.collection(collectionKey).doc(doc.id).update({Winner:"RCB"});
		}
		if(doc.data().Winner == 'Sunrisers Hyderabad'){
			firestore.collection(collectionKey).doc(doc.id).update({Winner:"SRH"});
		}		
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});
	});
}).catch((error) => {
   console.error("Error reading Collection: ", error);
});