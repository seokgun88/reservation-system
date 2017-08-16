define([], function() {
  var WEEK_DAYS = ["일", "월", "화", "수", "목", "금", "토"];

  function getFormatDate(inputDate) {
    var date = new Date(inputDate);

    return date.getFullYear() + "." +
      (date.getMonth() + 1) + "." +
      date.getDate() + "." + "(" + WEEK_DAYS[new Date(inputDate).getDay()] + ")";
  }

  function getPeriod(start, end) {
    return getFormatDate(start) + "~" + getFormatDate(end);
  }

  function getPriceDescription(priceType, price) {
    return priceType.typeName + priceType.dsc + " " + price.toLocaleString() + "원";
  }

  return {
    getFormatDate: getFormatDate,
    getPeriod: getPeriod,
    getPriceDescription: getPriceDescription
  };
});
