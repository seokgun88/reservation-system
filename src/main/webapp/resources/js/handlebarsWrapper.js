define(["jquery", "Handlebars"], function($, Handlebars) {

  var templates = [];

  function drawHandlerbarsTemplate(templateId, parentTag, data, type) {
    if (!templates[templateId]) {
      templates[templateId] = Handlebars.compile($(templateId).html());
    }
    type = type ? type : "html";
    $(parentTag)[type](templates[templateId](data));
  }

  return {
    drawHandlerbarsTemplate: drawHandlerbarsTemplate
  };
});
