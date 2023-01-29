importScripts("https://www.gstatic.com/firebasejs/9.15.0/firebase-app-compat.js");
importScripts("https://www.gstatic.com/firebasejs/9.15.0/firebase-messaging-compat.js");

 const firebaseConfig = {
                                ...
 };

 firebase.initializeApp(firebaseConfig);


 self.addEventListener('push', function(event) {
     const payload = event.data.json();
     const title = payload.notification.title;
     const options = {
         body: payload.notification.body,
         icon: payload.notification.icon,
         data: payload.notification.click_action
     };

     console.log(title)
     console.log(options)

     event.waitUntil(self.registration.showNotification(title, options));
 });

 self.addEventListener('notificationclick', function(event) {
     console.log(event.notification);
     event.notification.close();
     event.waitUntil(
         clients.openWindow(event.notification.data)
     );
 });