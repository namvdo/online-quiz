const startTimer = (duration, display) => {
    let timer = duration, minutes, seconds;
    setInterval(function () {
        minutes = Math.floor(parseInt(timer / 60, 10));
        seconds = Math.floor(parseInt(timer % 60, 10));

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            location.href = "localhost:8080/OnlineQuiz/QuizResult"
        }
    }, 1000);
}
window.onload = function() {
    let oneMinute = 60;
    let display = document.querySelector("#time");
    
}