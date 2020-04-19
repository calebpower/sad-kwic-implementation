$(document).ready(function () {
  start();

  const baseUrl = "http://localhost:9569/api/content";

  const settings = {
    scrapeEnabled: true,
  };

  function start() {
    addListeners();
  }

  function addListeners() {
    $("#search-form").on("submit", function (e) {
      e.preventDefault();
      const content = $("#search-query").val();
      queryDb(content);
    });
    $("#saveBtn").on("click", function () {
      const input = $("#db-content").val().trim();
      if (input.length > 0) saveInputToDb(input);
      else showAlert("warning", "A valid text input is required");
    });
    $("#icon-scrapeBtn").on("click", toggleScrape);
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

      $("#db-infoBtn").html(
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

  async function queryDb(content) {
    if (content.length <= 0) {
      return showAlert("warning", "A valid text input is required");
    }

    const query = content.trim().split(" ");

    try {
      $("#loader").toggleClass("hidden");
      const response = await fetch(baseUrl + `?q=${query.join("&q=")}`);
      const json = await response.json();

      console.log(json);
      let entries = "";

      for (const entry of json.entries) {
        entries += ` <small class="d-block bg-light py-1"><a href="${tagStripper(
          entry.url
        )}" target="_blank" class="text-dark p-1 px-2">
        <img src="${await getFavicon(
          entry.url
        )}" alt="🌐"  onerror="this.style.display='none'" width="40"/> ${tagStripper(
          entry.description
        )}</a></small>`;
      }

      showResults(entries);
    } catch (e) {
      showAlert("danger", e);
    } finally {
      $("#loader").toggleClass("hidden");
    }
  }

  function showResults(results) {
    $("#search-content").html(results);
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

  function toggleScrape() {
    settings.scrapeEnabled = !settings.scrapeEnabled;

    if (settings.scrapeEnabled) {
      $("#icon-scrapeBtn").html(
        `<span class="text-success">⚙️ Icon scraping enabled (slow) - click to disable.</span>`
      );
    } else {
      $("#icon-scrapeBtn").html(
        `<span class="text-danger">⚙️ Icon scraping disabled (fast) - click to enable.</span>`
      );
    }
  }

  // utilities
  function tagStripper(html) {
    return html.replace(/<\/?[^>]+(>|$)/g, "");
  }

  async function getFavicon(url) {
    if (!settings.scrapeEnabled) return "";

    try {
      const domain = url.match(
        /([http|https]+:)\/\/(w{3}\.)?([\w|\d|\.]+)/i
      )[3];
      const response = await fetch(
        "http://favicongrabber.com/api/grab/" + domain
      );
      const json = await response.json();

      return json.icons[0].src;
    } catch (e) {
      return "";
    }
  }
});
