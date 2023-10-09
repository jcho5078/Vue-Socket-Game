
function isEmpty(param){
    if(param == null||param == ''||param == 'null'||param == "undefined"||param == undefined) return true;
    else return false;
}

export {isEmpty};