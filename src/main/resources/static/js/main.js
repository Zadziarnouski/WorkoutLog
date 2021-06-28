/***Event Listener on Button**/

//countdown-timer.html
function countdownTimer(minutes) {

    document.getElementById('form2').style.display = "none";

    function minTwoDigits(n) {
        return (n < 10 ? '0' : '') + n;
    }

    let interval = 60 * minutes;
    let counter = setInterval(function () {
        let hours = Math.floor((interval % (60 * 60 * 24)) / (60 * 60));
        let minutes = Math.floor((interval % (60 * 60)) / (60));
        let seconds = Math.floor(interval % 60);

        interval--;

        document.getElementById("timer").innerHTML = minTwoDigits(hours) + " : " + minTwoDigits(minutes) + " : " + minTwoDigits(seconds);



        if (interval === 0) {
            clearInterval(counter);
            $('#timer').hide()
            document.getElementById('form2').submit();
        }
    }, 1000)
}

//create-update-exercise.html
function validateFormOfExercise() {
    let name = document.forms["form"]["name"].value;
    let muscleGroup = document.forms["form"]["muscleGroup"].value;
    let exerciseType = document.forms["form"]["exerciseType"].value;
    let necessaryEquipment = document.forms["form"]["necessaryEquipment"].value;
    if (name === "" || muscleGroup === "" || exerciseType === "" || necessaryEquipment === "") {
        alert("All fields must be filled out");
        return false;
    }
}

//create-update-exercise-in-workout.html
function validateFormOfExerciseInWorkout() {
    let name = document.forms["form"]["name"].value;
    let muscleGroup = document.forms["form"]["muscleGroup"].value;
    let exerciseType = document.forms["form"]["exerciseType"].value;
    let necessaryEquipment = document.forms["form"]["necessaryEquipment"].value;
    if (name === "" || muscleGroup === "" || exerciseType === "" || necessaryEquipment === "") {
        alert("All fields must be filled out");
        return false;
    }
}

//create-update-measurement.html
function validateFormOfMeasurement() {
    let weight = document.forms["form"]["weight"].value;
    let height = document.forms["form"]["height"].value;
    let arms = document.forms["form"]["arms"].value;
    let forearms = document.forms["form"]["forearms"].value;
    let chest = document.forms["form"]["chest"].value;
    let waist = document.forms["form"]["waist"].value;
    let buttocks = document.forms["form"]["buttocks"].value;
    let thighs = document.forms["form"]["thighs"].value;
    let calves = document.forms["form"]["calves"].value;

    if (weight === "" || height === "" || arms === "" || forearms === "" || chest === "" || waist === "" || buttocks === "" || thighs === "" || calves === "") {
        alert("All fields must be filled out");
        return false;
    }
}
//create-update-user.html
function validateFormOfUser() {
    let username = document.forms["form"]["username"].value;
    let firstname = document.forms["form"]["firstname"].value;
    let lastname = document.forms["form"]["lastname"].value;


    if (username === "" || firstname === "" || lastname === "") {
        alert("All fields must be filled out");
        return false;
    }
}

//login.html
function validateForm() {
    let username = document.forms["form"]["username"].value;
    let password = document.forms["form"]["password"].value;

    if (username === "") {
        alert("Field Username must be filled out");
        return false;
    }
    if (password === "") {
        alert("Field Password must be filled out");
        return false;
    }
}

//personal-profile.html
function validateFormOfProfile() {
    let name = document.forms["form"]["name"].value;
    let muscleGroup = document.forms["form"]["muscleGroup"].value;
    let exerciseType = document.forms["form"]["exerciseType"].value;
    let necessaryEquipment = document.forms["form"]["necessaryEquipment"].value;
    if (name === "" || muscleGroup === "" || exerciseType === "" || necessaryEquipment === "") {
        alert("All fields must be filled out");
        return false;
    }
}

//registration.html
function validateFormOfRegistration() {
    let username = document.forms["form"]["username"].value;
    let password = document.forms["form"]["password"].value;
    let firstname = document.forms["form"]["firstname"].value;
    let lastname = document.forms["form"]["lastname"].value;
    let gender = document.forms["form"]["gender"].value;
    let birthday = document.forms["form"]["birthday"].value;
    let height = document.forms["form"]["height"].value;
    let weight = document.forms["form"]["weight"].value;
    if (username === "" || password === "" || firstname === "" || lastname === "" || gender === ""
        || birthday === "" || height === "" || weight === "") {
        alert("All fields must be filled out");
        return false;
    }
    if (weight < 0 || height < 0) {
        alert("Field Height and Weight must be positive");
        return false;
    }
}

//update-workout-before-saving.html
function validateFormBeforeSaving() {
    let title = document.forms["form"]["title"].value;
    let rating = document.forms["form"]["rating"].value;

    if (title === "" || rating === "") {
        alert("All fields must be filled out except for comments");
        return false;
    }
}

//workout.html
function validateFormOfWorkout() {
    let resultOfSet = document.forms["form2"]["resultOfSet"].value;

    if (resultOfSet === "") {
        alert("Field Result of Set must be filled out");
        return false;
    }
}

function startTimerAfterSetResult() {
    countdownTimer2(1)
}

function countdownTimer2(minutes) {

    // document.getElementById('form1').style.display = "none";
    document.getElementById('form2').style.display = "none";

    function minTwoDigits(n) {
        return (n < 10 ? '0' : '') + n;
    }

    // let interval = minutes * 60;  //5 minutes = 300 sec
    let interval = 3;  //5 minutes = 300 sec
    let counter = setInterval(function () {
        let hours = Math.floor((interval % (60 * 60 * 24)) / (60 * 60));
        let minutes = Math.floor((interval % (60 * 60)) / (60));
        let seconds = Math.floor(interval % 60);

        interval--;

        document.getElementById("timer").innerHTML = minTwoDigits(hours) + " : " + minTwoDigits(minutes) + " : " + minTwoDigits(seconds);

        if (interval === 0) {
            clearInterval(counter);
            $('#timer').hide()
            document.getElementById('form1').submit();
        }
    }, 1000)
}







