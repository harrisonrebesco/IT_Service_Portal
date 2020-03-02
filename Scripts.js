function getTime() {
    // Description - this function provides a continuously updating clock
    //               to show on MainPage
    var clock = new Date();

    var d = clock.getDate();
    var mo = clock.getMonth() + 1; // to adjust from 0-11 range of Date
    var y = clock.getFullYear() - 2000;
        // This assumes that this webpage has no longevity and time travel
        // does not exist. Will quickly change year to 2 digits.

    var h = clock.getHours();
    var m = clock.getMinutes();
    var s = clock.getSeconds();
    
    // Check if any date components are a single character
    // and if so, convert them to two characters
    if ( y < 10 ) { y = "0" + y; }
    if ( d < 10 ) { d = "0" + d; }
    if ( mo < 10 ) { mo = "0" + mo; }
    if ( h < 10 ) { h = "0" + h; }
    if ( m < 10 ) { m = "0" + m; }
    if ( s < 10 ) { s = "0" + s; }

    var out =d+"/"+mo+"/"+y+" "+h+":"+m+":"+s;
    return out;
}

function enableTagEdit() {
    document.getElementById("TagsBlock").hidden = "hidden"; // TagsBlock becomes hidden
    document.getElementById("TagsInputBlock").hidden = ""; // TagsInputBlock becomes unhidden  
    document.getElementById("EditTagsBlock").hidden = "hidden";"EditTagsBlock"
}