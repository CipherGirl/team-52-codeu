/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterUsername;
  document.title = parameterUsername + ' - User Page';
}

/**
 * Shows the message form if the user is logged in and viewing their own page.
 */
function showMessageFormIfViewingSelf() {
  fetch('/login-status')
      .then((response) => {
        return response.json();
      })
      .then((loginStatus) => {
        if (loginStatus.isLoggedIn &&
            loginStatus.username == parameterUsername) {
          const messageForm = document.getElementById('message-form');
          const imageForm = document.getElementById('my-form');
          messageForm.classList.remove('hidden');
          imageForm.classList.remove('hidden');
        }
      });
  document.getElementById('about-me-form').classList.remove('hidden');
}

/** Fetches messages and add them to the page. */
function fetchMessages() {
  const url = '/messages?user=' + parameterUsername;
  fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((messages) => {
        const messagesContainer = document.getElementById('message-container');
        const nMessagesContainer = document.getElementById('n-message-container');
        if (messages.length == 0) {
          messagesContainer.innerHTML = '<p>This user has no posts yet.</p>';
          nMessagesContainer.innerHTML = '<p>This user has no posts yet.</p>';
        } else {
          messagesContainer.innerHTML = '';
          nMessagesContainer.innerHTML = '';
        }
        messages.forEach((message) => {
          const messageDiv = buildMessageDiv(message);
          if (message.sscore >= 0.0){
            messagesContainer.appendChild(messageDiv);
          }
          else{
            nMessagesContainer.appendChild(messageDiv);
          }
        });
      });
}

function fetchImages(){
    const addr =  '/get-images';
    fetch(addr)
          .then((response) => {
            return response.json();
          })
          .then((imgs) => {
            const imagesContainer = document.getElementById('img-container');
            if (imgs.length == 0) {
                      imagesContainer.innerHTML = '<p>This user has no pictures yet.</p>';

            } else {
                      imagesContainer.innerHTML = '';

            }
            imgs.forEach((image) => {
            const imageDiv = buildImageDiv(image);
            imagesContainer.appendChild(imageDiv);
            });

          });

}


/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildMessageDiv(message) {
  sentiment = ""
  if (message.sscore >= 0.0) {sentiment = "Positive";}
  else{sentiment = "Negative";}


  const headerDiv = document.createElement('div');
  headerDiv.classList.add('message-header');
  headerDiv.appendChild(document.createTextNode(
      message.user + ' - ' + new Date(message.timestamp) +"   -- "+ message.sscore));

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('message-body');
  bodyDiv.innerHTML = message.text;

  const messageDiv = document.createElement('div');
  messageDiv.classList.add('message-div');
  messageDiv.appendChild(headerDiv);
  messageDiv.appendChild(bodyDiv);

  return messageDiv;
}

function buildImageDiv(image) {



  const headerDiv = document.createElement('div');
  headerDiv.classList.add('image-header');
  headerDiv.appendChild(document.createTextNode(
      image.user + ' - ' + new Date(image.timestamp)));

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('image-body');


  bodyDiv.innerHTML = image.url;

  const imageDiv = document.createElement('div');
  imageDiv.classList.add('image-div');
  imageDiv.appendChild(headerDiv);
  imageDiv.appendChild(bodyDiv);

  return imageDiv;
}


/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  showMessageFormIfViewingSelf();
  fetchMessages();
  fetchAboutMe();
  fetchImages();
  ClassicEditor.create( document.getElementById('message-input') );
}
function fetchAboutMe(){
  const url = '/about?user=' + parameterUsername;
  fetch(url).then((response) => {
    return response.text();
  }).then((aboutMe) => {
    const aboutMeContainer = document.getElementById('about-me-container');
    if(aboutMe == ''){
      aboutMe = 'This user has not entered any information yet.';
    }
    
    aboutMeContainer.innerHTML = aboutMe;

  });
}

function hideNegative(){
    document.getElementById('n-message-container').style.display='none';
}

function showNegative(){
    document.getElementById('n-message-container').style.display='block';
}