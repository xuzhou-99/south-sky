const signInBtn = document.getElementById("signIn");
const signUpBtn = document.getElementById("signUp");
const fistForm = document.getElementById("form1");
const secondForm = document.getElementById("form2");
const container = document.querySelector(".container");


signInBtn.addEventListener("click", () => {
    container.classList.remove("right-panel-active");
});

signUpBtn.addEventListener("click", () => {
    container.classList.add("right-panel-active");
});

fistForm.addEventListener("submit", (e) => e.preventDefault());
secondForm.addEventListener("submit", (e) => e.preventDefault());

/**
 * 注册
 */
function register() {
    console.log(fistForm);

    var formData = new FormData();
    var xhr = new XMLHttpRequest();


    var username = document.getElementsByName("username")[0].value;
    var email = document.getElementsByName("email")[0].value;
    var password = document.getElementsByName("password")[0].value;

    formData.append("username", username);
    formData.append("email", email);
    formData.append("password", password);
    xhr.open('POST', 'register', false);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var result = JSON.parse(xhr.responseText);

            if (result.code == 200) {
                console.log(result);
                // callback(xhr.responseText);
                alert("注册成功！请登录")
                document.getElementsByName("username")[0].value = '';
                document.getElementsByName("email")[0].value = '';
                document.getElementsByName("password")[0].value = '';
            } else {
                alert("注册失败，请重新尝试！" + result.message)
            }
        }
    }

    xhr.send(formData);
}

/**
 * 登录
 */
function login() {
    console.log(secondForm);

    var formData = new FormData();
    var xhr = new XMLHttpRequest();

    var email = document.getElementsByName("email")[1].value;
    var password = document.getElementsByName("password")[1].value;
    if (email == '') {
        alert("邮箱不能为空")
        return;
    }
    if (password == '') {
        alert("密码不能为空")
        return;
    }
    var search = window.location.search;
    formData.append("email", email);
    formData.append("password", password);
    xhr.open('POST', 'login' + search, false);

    xhr.onreadystatechange = function () {
        if (!xhr || (xhr.readyState !== 4)) {
            return;
        }
        var result;
        try {
            result = JSON.parse(xhr.responseText);
        } catch (e) {
            result = xhr.responseText;
        }

        // callback(xhr.responseText);
        if (xhr.status === 200) {
            alert("登录成功!")
            location.assign(result);
        } else {
            alert("登录失败！" + JSON.stringify(xhr.responseText));
        }
    }

    xhr.send(formData);
}