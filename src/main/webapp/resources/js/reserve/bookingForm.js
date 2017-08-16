define(["jquery", "util", "egComponent"],
  function($, Util, egComponent) {
    "use strict";

    var regexTel = /0\d{1,2}-\d{3,4}-\d{4}/;
    var regexEmail = /[0-9a-zA-Z]+@[0-9a-zA-Z]+\.[0-9a-zA-Z]+/;

    var bookingForm = Util.extend(egComponent, {
      init: function() {
        this.$container = $(".section_booking_form");
        this.$name = this.$container.find("#name");
        this.$tel = this.$container.find("#tel");
        this.$email = this.$container.find("#email");
        this.$chkAgree = $("#chk3");
        this.$totalCount = $(".total_count");

        this.validateFlag = true;

        this.initEvent();
      },
      initEvent: function() {
        this.$name.on("keyup", this.validate.bind(this));
        this.$tel.on("keyup", this.validate.bind(this));
        this.$email.on("keyup", this.validate.bind(this));
        this.$chkAgree.on("click", this.validate.bind(this));

        $(".ico_minus3").on("click", this.validate.bind(this));
        $(".ico_plus3").on("click", this.validate.bind(this));
      },
      delay: function(time) {
        return new Promise(function(resolve) {
          setTimeout(function() {
            this.isValid = (parseInt(this.$totalCount.text()) > 0) &&
              (this.$name.val() ? true : false) &&
              regexTel.test(this.$tel.val()) &&
              regexEmail.test(this.$email.val()) &&
              this.$chkAgree.prop("checked");

            if (this.isValid) {
              this.trigger("validate");
            } else {
              this.trigger("unvalidate");
            }
            resolve("success!");
          }.bind(this), time);
        }.bind(this));
      },
      validate: function() {
        if (this.validateFlag) {
          this.validateFlag = false;
          this.delay(300).then(function(data) {
            this.validateFlag = true;
          }.bind(this));
        }
      },
      getBookingUser: function() {
        return {
          userName: this.$name.val(),
          tel: this.$tel.val(),
          email: this.$email.val()
          // tickets: [],
          // totalCount: 0,
          // totalPrice: 0
        };
      }
    });
    return bookingForm;
  });
