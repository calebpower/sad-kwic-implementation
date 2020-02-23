$(document).ready(function() {
  start();

  const baseUrl = "";

  function start() {
    $("#code").focus();
    addListeners();
  }

  function addListeners() {
    $("#code").on("input", function() {});
    $("#submit").click(function() {
      const content = getContent();
      parseToKWIC(content);
    });
  }

  function getContent() {
    // get all lines and push them to content
    content = "";
    $(".line").each(function() {
      content +=
        $(this)
          .text()
          .trim() + "\\n";
    });

    return content;
  }

  function setPreviewContent(text) {
    $("#preview").text(text);
  }

  async function parseToKWIC(text) {
    /*     fetch("http://example.com", {
      method: "post",
      body: JSON.stringify({ text }),

      headers: {
        Accept: "application/json",
      },

      credentials: "same-origin", // send cookies
      credentials: "include" // send cookies, even in CORS
    }).then(data=>{

    }).catch(error=>{
        console.error(error)
    }).finally((txt)=>{
        //demo
        setPreviewContent(text)
    }) */

    $('#loader').css('display','flex')
    await demoServer();
    $('#loader').css('display','none')
    setPreviewContent(text);
  }

  function demoServer() {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(null);
      }, 3000);
    });
  }
});
