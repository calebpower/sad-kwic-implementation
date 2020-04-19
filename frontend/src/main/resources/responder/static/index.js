$(document).ready(function () {
  start();

  const baseUrl = "http://localhost:4567/api/content";

  function start() {
    addListeners();
  }

  function addListeners() {
    $("#search-form").on("submit", function (e) {
      e.preventDefault();
      const content = $("#search-query");
      queryDb(content);
    });
    $("#saveBtn").on("click", function () {
      const input = $("#db-content").val().trim();
      if (input.length > 0) saveInputToDb(input);
      else showAlert("warning", "A valid text input is required");
    });
  }

  async function saveInputToDb(input) {
    const entries = [];

    try {
      const lines = input.split("\n");

      for (line of lines) {
        const data = line.match(
          /([\w*|\d*|\s{1}]+)(http|https)(:\/\/[\w|\.|\d]+)/i
        );
        entries.push({
          url: data[2].toString().trim() + data[3].toString().trim(),
          description: data[1].toString().trim(),
        });
      }
      console.log(JSON.stringify({ entries }));
    } catch (error) {
      return showAlert("warning", "Invalid input format");
    }

    try {
      $("#loader").toggleClass("hidden");
      const response = await fetch(baseUrl, {
        method: "post",
        body: JSON.stringify({ entries }),
      });
      const json = await response.json();
      console.log(json);

      $("#db-info").html(
        "<span class='text-success' >📑 Database initialized - click to change input.</span>"
      );
    } catch (e) {
      showAlert("danger", e);
      $("#db-info").html(
        "<span class='text-danger' >📑 Database empty - click to enter input.</span>"
      );
    } finally {
      $("#loader").toggleClass("hidden");
    }
  }

  function getContent() {
    // get all lines and push them to content
    const content = [];
    $(".line").each(function () {
      const text = $(this).text().trim();
      if (text) content.push(text);
    });

    return content;
  }

  async function queryDb(content) {
    if (content.length <= 0) {
      return showAlert("warning", "A valid text input is required");
    }

    console.log(content);

    try {
      $("#loader").toggleClass("hidden");
      const response = await fetch(baseUrl, {
        method: "post",
        body: JSON.stringify({ q: content }),
      });
      const json = await response.json();
      let entries = "";

      for (const entry of json.entries) {
        entries += ` <small class="d-block bg-light py-1"><a href="${tagStripper(
          entry.url
        )}" target="_blank" class="text-dark p-1 px-2">
        <img src="${await getFavicon(entry.url)}" width="40"/> ${tagStripper(
          entry.description
        )}</a></small>`;
      }
      setPreviewContent(entries);
    } catch (e) {
      showAlert("danger", e);
    } finally {
      $("#loader").toggleClass("hidden");
    }
  }

  function showAlert(type, msg) {
    $("#alert").html(`
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                <strong>Error!</strong> ${msg}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            `);
  }

  // utilities
  function tagStripper(html) {
    return html.replace(/<\/?[^>]+(>|$)/g, "");
  }

  getFavicon("http://www.uco.edu");
  async function getFavicon(url) {
    try {
      const domain = url.match(
        /([http|https]+:)\/\/(w{3}\.)?([\w|\d|\.]+)/i
      )[3];
      const response = await fetch(
        "http://favicongrabber.com/api/grab/" + domain
      );
      const json = await response.json()

      return json.icons[0].src

    } catch (e) {
      return "🌐";
    }
  }
});
