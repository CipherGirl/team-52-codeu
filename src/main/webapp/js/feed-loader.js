// Fetch messages and add them to the page.
  function fetchMessages(){
    const url = '/feed';
    fetch(url).then((response) => {
      return response.json();
    }).then((messages) => {
      const messageContainer = document.getElementById('message-container');
      const nMessagesContainer = document.getElementById('n-message-container');
      if(messages.length == 0){
       messageContainer.innerHTML = '<p>There are no posts yet.</p>';
       nMessagesContainer.innerHTML = '<p>There are no negative posts.</p>';
      }
      else{
       messageContainer.innerHTML = '';
       nMessagesContainer.innerHTML = '';
      }
      messages.forEach((message) => {
      const messageDiv = buildMessageDiv(message);
      if (message.sscore >= 0.0){
       messageContainer.appendChild(messageDiv);
       }
      else{
       nMessagesContainer.appendChild(messageDiv);
      }
      });
    });
  }

  function buildMessageDiv(message){
   const usernameDiv = document.createElement('div');
   usernameDiv.classList.add("left-align");
   usernameDiv.appendChild(document.createTextNode(message.user));

   const timeDiv = document.createElement('div');
   timeDiv.classList.add('right-align');
   timeDiv.appendChild(document.createTextNode(new Date(message.timestamp)));

   const headerDiv = document.createElement('div');
   headerDiv.classList.add('message-header');
   headerDiv.appendChild(usernameDiv);
   headerDiv.appendChild(timeDiv);

   const bodyDiv = document.createElement('div');
   bodyDiv.classList.add('message-body');
   //bodyDiv.appendChild(document.createTextNode(message.text));
   bodyDiv.innerHTML = message.text;

   const messageDiv = document.createElement('div');
   messageDiv.classList.add("message-div");
   messageDiv.appendChild(headerDiv);
   messageDiv.appendChild(bodyDiv);

   return messageDiv;
  }

  // Fetch data and populate the UI of the page.
  function buildUI(){
   fetchMessages();
  }
  //functions to show and hide negative messages
  function hideNegative(){
      document.getElementById('n-message-container').style.display='none';
  }

  function showNegative(){
      document.getElementById('n-message-container').style.display='block';
  }