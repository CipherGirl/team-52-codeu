// Fetch messages and add them to the page.
  function fetchGMessages(){
    const url = '/group';
    fetch(url).then((response) => {
      return response.json();
    }).then((gmessages) => {
      const messageContainer = document.getElementById('group-message-container'); //group-message-container

      if(gmessages.length == 0){
       messageContainer.innerHTML = '<p>There are no posts yet.</p>';

      }
      else{
       messageContainer.innerHTML = 'Posts:';

      }
      gmessages.forEach((gmessage) => {
      const messageDiv = buildGMessageDiv(gmessage);

       messageContainer.appendChild(messageDiv);


      });
    });
  }

  function buildGMessageDiv(gmessage){
   const usernameDiv = document.createElement('div');
   usernameDiv.classList.add("left-align");
   usernameDiv.appendChild(document.createTextNode(gmessage.user));

   const timeDiv = document.createElement('div');
   timeDiv.classList.add('right-align');
   timeDiv.appendChild(document.createTextNode(new Date(gmessage.timestamp)));

   const headerDiv = document.createElement('div');
   headerDiv.classList.add('message-header');
   headerDiv.appendChild(usernameDiv);
   headerDiv.appendChild(timeDiv);

   const bodyDiv = document.createElement('div');
   bodyDiv.classList.add('message-body');
   bodyDiv.appendChild(document.createTextNode(gmessage.grouptext));

   const messageDiv = document.createElement('div');
   messageDiv.classList.add("message-div");
   messageDiv.appendChild(headerDiv);
   messageDiv.appendChild(bodyDiv);

   return messageDiv;
  }

  // Fetch data and populate the UI of the page.
  function buildUI(){
   fetchGMessages();
  }
