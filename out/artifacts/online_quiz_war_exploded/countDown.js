/**
 *
 * @param duration - the amount of time for taking quiz
 * @param display - the element to display the timing
 */
function startTimer(duration, display) {
    let timer = duration, minutes, seconds;
    setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        let answersId = getChosenAnswers();
        let quizId = document.getElementById("quizId").value;
        display.textContent = minutes + ":" + seconds;
        console.log("quiz id: " + quizId  + " answer ids: " + answersId);
        if (--timer < 0) {

            let location = "TakeQuiz?end=true&quizid=" + quizId;
            for(let i = 0; i < answersId.length; i++) {
                location += "&answer" + (i + 1) + "=" + answersId[i];
            }
            window.location.replace(location);

        }
        sessionStorage.setItem("time", timer);
        console.log("from countDown: " + sessionStorage.getItem("time"));
    }, 1000);
}

const getChosenAnswers = () => {
    let answers = document.getElementsByClassName("answer-checkbox");
    let answersId = [];

    for(let i = 0; i < answers.length; i++) {
        if (answers[i].checked) {
            answersId.push(answers[i].value);
        }
    }

    return answersId;


}
window.onload = function() {
    const urlParams = new URLSearchParams(window.location.search);
    const time = parseInt(urlParams.get("totalTime"));
    console.log("total time: " + time);
    let display = document.querySelector("#time");
    if (sessionStorage.getItem("time") == null) {
        startTimer(time - 1, display);
    } else {
        let timeNow = sessionStorage.getItem("time");
        console.log(timeNow);
        startTimer(timeNow, display)
    }
}

