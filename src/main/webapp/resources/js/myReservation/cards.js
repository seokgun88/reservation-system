define([], function(){
  return [{
      "templateId": "#notused-template",
      "targetTag": ".list_cards .unused"
    }, {
      "templateId": "#confirmed-template",
      "targetTag": ".list_cards .confirmed"
    }, {
      "templateId": "#used-template",
      "targetTag": ".list_cards .used:not(.canceled)"
    }, {
      "templateId": "#canceled-template",
      "targetTag": ".list_cards .canceled"
    }];
});
