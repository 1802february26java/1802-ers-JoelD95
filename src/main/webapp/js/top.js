window.onscroll = function() {scrollFunction()};
document.getElementById("scroll").addEventListener("click", topFunction);

function scrollFunction() {
    if (document.body.scrollTop > 10 || document.documentElement.scrollTop > 10) {
        document.getElementById("scroll").className = "btn-md btn-info btn-block2";
    }
}


function topFunction() {
    document.body.scrollTop = 0; 
    document.documentElement.scrollTop = 0; 
}