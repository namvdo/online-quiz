function startTimer(duration, display) {
    let timer = duration, minutes, seconds;
    setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            window.location.replace("quizResult.jsp");
            sessionStorage.removeItem("time");
        }
        sessionStorage.setItem("time", timer);
        console.log("from countDown: " + sessionStorage.getItem("time"));
    }, 1000);
}
window.onload = function() {
    let display = document.querySelector("#time");
    if (sessionStorage.getItem("time") == null) {
        startTimer(60, display);
    } else {
        let timeNow = sessionStorage.getItem("time");
        console.log(timeNow);
        startTimer(timeNow, display)
    }
}

