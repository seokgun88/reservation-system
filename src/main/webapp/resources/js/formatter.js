define([], function() {
  var WEEK_DAYS = ["일", "월", "화", "수", "목", "금", "토"];

  function getFormatDate(inputDate) {
    var date = new Date(inputDate);

    return date.getFullYear() + "." +
      (date.getMonth() + 1) + "." +
      date.getDate() + "." + "(" + WEEK_DAYS[new Date(inputDate).getDay()] + ")";
  }

  function getPeriod(start, end) {
    return getFormatDate(start) + " ~ " + getFormatDate(end);
  }

  function getPriceDescription(priceType, price) {
    return priceType.typeName + priceType.dsc + " " + price.toLocaleString() + "원";
  }

  function getTicketCountString(generalCount, youthCount, childCount) {
    var countString = [];
    if(generalCount!=0){
      countString.push("성인("+generalCount+")");
    }
    if(youthCount!=0){
      countString.push("청소년("+youthCount+")");
    }
    if(childCount!=0){
      countString.push("어린이("+childCount+")");
    }
    var totalCount = generalCount + youthCount + childCount;
    return countString.join(", ") + " - 합계(" + totalCount + ")";
  }

  return {
    getFormatDate: getFormatDate,
    getPeriod: getPeriod,
    getPriceDescription: getPriceDescription,
    getTicketCountString: getTicketCountString
  };
});
