//JAVASCRIPT CODE (G SignIn)
/*function onSignIn(googleUser){
    console.log('Logged in as: ' + googleUser.getBasicProfile().getName());

    var profile = googleUser.getBasicProfile();
    $("#my-signin2").css("display", "none");
    $(".data").css("display", "block");
    $("#pic").attr('src',profile.getImageUrl());
    $("#email").text(profile.getEmail());
    $("#name").text(profile.getName());
}*/


//SERVLET CODE (G SignIn)
function onSignIn(googleUser) {
         var profile = googleUser.getBasicProfile();
         console.log('Name: ' + profile.getName());
         console.log('Image URL: ' + profile.getImageUrl());
         console.log('Email: ' + profile.getEmail());
         console.log('id_token: ' + googleUser.getAuthResponse().id_token);

         $("#g-signin2").css("display", "none");
         var redirectUrl = 'login';

         //using jquery to post data dynamically
         var form = $('<form action="' + redirectUrl + '" method="post">' +
                         '<input type="text" name="id_token" value="' +
                          googleUser.getAuthResponse().id_token + '" />' +
                                                                 '</form>');
          $('body').append(form);
          form.submit();

}

function signOut(){
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function(){

    alert("You have been successfully signed out.");

    $("#my-signin2").css("display","flex");
    $(".data").css("display","none");

    });
}
function onFailure(error) {
    console.log(error);
}
function renderButton() {
    gapi.signin2.render('my-signin2', {
    'scope': 'profile email',
    'width': 240,
    'height': 50,
    'longtitle': true,
    'theme': 'dark',
    'onsuccess': onSignIn,
    'onfailure': onFailure
    });
}