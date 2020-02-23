$(document).ready(function () {
    start();

    const baseUrl = "http://localhost:4567/api/content";

    function start() {
        $("#code").focus();
        addListeners();
    }

    function addListeners() {
        $("#code").on("input", function () {
            if (getContent().length === 0) {
                $('.line').html('&nbsp;')
            }
        });
        $("#submit").on('click', function () {
            const content = getContent();
            parseToKWIC(content);
        });
    }

    function getContent() {
        // get all lines and push them to content
        const content = [];
        $(".line").each(function () {
            const text = $(this).text().trim()
            if(text) content.push(text)
        });

        return content;
    }

    function setPreviewContent(text) {
        $("#preview").html(text);
    }

    async function parseToKWIC(content) {
        if(content.length <= 0){
            showAlert('warning','A valid text input is required')
            return;
        }

        $('#loader').toggleClass('hidden')

        try {
            const response = await fetch(baseUrl, {
                method: "post",
                body: JSON.stringify({lines: content})
            })
            const json = await response.json()
            let lines = ""

            for (const line of json.lines) {
                lines += `<div>${tagStripper(line)}</div>`
            }
            setPreviewContent(lines);

        } catch (e) {
            showAlert('danger',e)
        } finally {
            $('#loader').toggleClass('hidden')
        }
    }

    function showAlert(type, msg){
        $('#alert').html(`
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                <strong>Error!</strong> ${msg}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            `)
    }

    // utilities
    const tagStripper = html => {
        return html.replace(/<\/?[^>]+(>|$)/g, '');
    };
});
