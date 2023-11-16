document.addEventListener("DOMContentLoaded", function () {
  flatpickr("#date_selector", {
    altInput: false,
    altFormat: "F j, Y", // 형식을 altFormat으로 변경
    mode: "multiple",
    dateFormat: "Y-m-d",
    inline: true,
    minDate: "today", // minDate는 문자열로 설정 가능
    maxDate: new Date().getFullYear() + 1 + "-12-31",
    disable: [
      function (date) {
        // return true to disable
        return date.getDay() === 0 || date.getDay() === 6;
      },
    ],
    locale: {
      months: {
        shorthand: [
          "1월",
          "2월",
          "3월",
          "4월",
          "5월",
          "6월",
          "7월",
          "8월",
          "9월",
          "10월",
          "11월",
          "12월",
        ],
        longhand: [
          "1월",
          "2월",
          "3월",
          "4월",
          "5월",
          "6월",
          "7월",
          "8월",
          "9월",
          "10월",
          "11월",
          "12월",
        ],
      },

      firstDayOfWeek: 1, // 월요일부터 시작
    },
  });

  flatpickr("#flatpickr_div", {
    altInput: "#date_selector",
    altFormat: "F j, Y",
    dateFormat: "Y-m-d",
  });
});
