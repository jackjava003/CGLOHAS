window.sr = ScrollReveal({ reset: true });
sr.reveal('.poster1-js',{ duration:1000,origin: 'bottom',scale:0.5});
sr.reveal('.poster1-js-resetoff',{ duration:1000,origin: 'bottom',scale:0.5,reset:false});
sr.reveal('.poster1-js-text',{ duration:1000,origin: 'left',delay:500,scale:0.5});
sr.reveal('.va-js-box', { duration: 500}, 50);
sr.reveal('.va-js-box1', { duration: 500}, 50);
sr.reveal('.va-js-box2', { duration: 500}, 50);
sr.reveal('.poster1-js-text-resetoff',{ duration:1000,origin: 'left',delay:500,scale:0.5,reset:false});



sr.reveal('.ab_us',{duration:1000,origin:'right',scale:0.5,reset:false});
sr.reveal('.ab_us_1',{duration:1000,origin:'right',scale:0.5,delay:500,reset:false});

sr.reveal('.ab_us_2',{duration:1000,origin:'right',scale:1,delay:500,reset:false});
sr.reveal('.ab_us_3',{duration:1000,origin:'left',scale:1,delay:500,reset:false});
sr.reveal('.ab_us_4',{duration:1000,origin:'right',scale:1,delay:500,reset:false});
sr.reveal('.ab_us_5',{duration:1000,origin:'left',scale:1,delay:500,reset:false});
sr.reveal('.ab_us_6',{duration:1000,origin:'right',scale:1,delay:500,reset:false});
sr.reveal('.ab_us_7',{duration:1000,origin:'left',scale:1,delay:500,reset:false});

sr.reveal('.li_us',{duration:1000,origin:'right',scale:0.5,reset:false});

sr.reveal('.ab_us_8',{duration:1000,origin:'left',scale:1,delay:500,reset:false});
sr.reveal('.ab_us_9',{duration:1000,origin:'left',scale:1,delay:1000,reset:false});
sr.reveal('.ab_us_10',{duration:1000,origin:'left',scale:1,delay:1500,reset:false});
sr.reveal('.ab_us_11',{duration:1000,origin:'left',scale:1,delay:2000,reset:false});
sr.reveal('.ab_us_12',{duration:1000,origin:'bottom',scale:1,delay:2500,reset:false});
sr.reveal('.ab_us_13',{duration:1000,origin:'top',scale:1,delay:500,reset:false});




$(function() {
    $('a.page-scroll').bind('click', function(event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top
        }, 1500, 'easeInOutExpo');
        event.preventDefault();
    });
});


var myIndex = 0;
carousel();

function carousel() {
    var i;
    var x = document.getElementsByClassName("mySlides");
//    var y = document.getElementsByClassName("mySlides1");
    for (i = 0; i < x.length; i++) {
       x[i].style.display = "none";
//       y[i].style.display = "none";
    }
    myIndex++;
    if (myIndex > x.length) {myIndex = 1}
    x[myIndex-1].style.display = "block";
//    y[myIndex-1].style.display = "block";
//    sr.reveal(x[myIndex-1],{duration:500,origin:'bottom',scale:1,});
//    sr.reveal(y[myIndex-1],{duration:500,origin:'bottom',scale:1,});
    setTimeout(carousel, 5000);
}


