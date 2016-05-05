//reference: modified from a open source code:http://www.softwhy.com/forum.php?mod=viewthread&tid=19849

$(document).ready(function () {
  // 1. username
  $("#username").focus(function () {
    elemFocus("username_msg", "4-20 characters or numbers");
 
  }).blur(userValidator);
  // 2. password
  $("#password").focus(function () {
    elemFocus("pwd_msg", "6-20 characters or numbers");
  }).blur(pwdValidator);
  // 3. check password
  $("#pwdRepeat").focus(function () {
    elemFocus("pwdRepeat_msg", "6-20 characters or numbers");
  }).blur(pwdRepeatValidator);
  $("#signupSub").click(function () {
    return validateForm();
  })
})

  // some common message
function elemFocus(eleId, text) {
  var ele_msg = $("#" + eleId);
  ele_msg.text(text);
  ele_msg.attr("class", "focus");
}
 
// check the user name 
function userValidator() {
  // get the input value
  var value = $("#username").val();
  
  var username_msg = $("#username_msg");
  
  if (value == "" || value == null) {
    username_msg.text("User name cannot be empty");
    username_msg.attr("class", "error");
    return false;
  } else if (value.length < 4 || value.length > 20) {
    username_msg.text("The length is not right");
    username_msg.attr("class", "error");
    return false;
  } else if (!/^[a-zA-Z0-9-_]{4,20}$/.test(value)) {
    username_msg.text("User name is not right");
    username_msg.attr("class", "error");
    return false;
  }
  // if modify and right, the message will hide
  if (!username_msg.hasClass("hide")) {
    username_msg.text("");
    username_msg.attr("class", "hide");
  }
  return true;
}

// check the password
function pwdValidator() {
  var value = $("#password").val();
  var pwd_msg = $("#pwd_msg");
  if (value == "" || value == null) {
    pwd_msg.text("password cannot be empty");
    pwd_msg.attr("class", "error");
    return false;
  } else if (value.length < 6 || value.length > 20) {
    pwd_msg.text("Password length is not right");
    pwd_msg.attr("class", "error");
    return false;
  } else if (!/^[a-zA-Z0-9]{6,20}$/.test(value)) {
    pwd_msg.text("The password is not right");
    pwd_msg.attr("class", "error");
    return false;
  }
  if (!pwd_msg.hasClass("hide")) {
    pwd_msg.text("");
    pwd_msg.attr("class", "hide");
  }
  return true;
}
// check check password function
function pwdRepeatValidator() {
  var value = $("#pwdRepeat").val();
  var pwdRepeat_msg = $("#pwdRepeat_msg");
  var pwd = $("#password").val();
  if (value == "" || value == null) {
    pwdRepeat_msg.text("password cannot be empty");
    pwdRepeat_msg.attr("class", "error");
    return false;
  } else if (value.length < 6 || value.length > 20) {
    pwdRepeat_msg.text("Password length is not right");
    pwdRepeat_msg.attr("class", "error");
    return false;
  } else if (!/^[a-zA-Z0-9]{6,20}$/.test(value)) {
    pwdRepeat_msg.text("The password is not right");
    pwdRepeat_msg.attr("class", "error");
    return false;
  } else if (value != pwd) {
    pwdRepeat_msg.text("Two passwords are different");
    pwdRepeat_msg.attr("class", "error");
    return false;
  }
  if (!pwdRepeat_msg.hasClass("hide")) {
    pwdRepeat_msg.text("");
    pwdRepeat_msg.attr("class", "hide");
  }
  return true;
}


function validateForm() {
  if (!userValidator() || !pwdValidator() || !pwdRepeatValidator()) {
    return false;
  }
  return true;
}