define(["formatter"], function(Formatter) {
  var reviewIdToImageIds = [];

  function getReviews(data) {
    var reviews = [];
    var productName = data.name;
    data.forEach(function(item) {
      var mainImageUri = "",
        imageCount = 0,
        hasImage = " hide";
      if (item.images) {
        reviewIdToImageIds[item.id] = item.images;
        mainImageUri = "/api/images/" + item.images[0];
        imageCount = item.images.length;
        hasImage = "";
      }
      var modifyDate = Formatter.getFormatDate(item.modifyDate);
      var review = {
        id: item.id,
        mainImageUri: mainImageUri,
        imageCount: imageCount,
        hasImage: hasImage,
        productName: productName,
        review: item.review,
        score: item.score / 10,
        user: item.userEmail.substr(0, 4) + "****",
        modifyDate: modifyDate
      };
      reviews.push(review);
    });
    return reviews;
  }

  function getReviewImageIds(reviewId) {
    return reviewIdToImageIds[reviewId];
  }

  return {
    getReviewImageIds: getReviewImageIds,
    getReviews: getReviews
  };
});
