$(document).ready(function () {
    $('#techStack').select2();
    $('#position').select2();


    $("#postForm").submit(function (event) { // form 전송 이벤트 처리
        event.preventDefault(); // 폼의 기본 동작을 중지. 이 경우에는 페이지가 새로고침되지 않도록 방지.

        const formData = {
            type: $("#type").val(),
            size: $("#size").val(),
            processType: $("#processType").val(),
            period: $("#period").val(),
            techStack: $("#techStack").select2('val'),
            deadline: $("#deadline").val(),
            position: $("#position").select2('val'),
            contactMethod: $("#contactMethod").val(),
            contactDetails: $("#contactDetails").val(),
            title: $("#title").val(),
            description: $("#description").val()
        };
        console.log(formData);

        $.ajax({
            url: "/api/posts",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                if (response) {
                    alert("저장되었습니다.");
                    location.replace("/")
                }
            },
            error: function (xhr) {
                alert(xhr.responseText);
                console.error("xhr.responseText", xhr.responseText);
            }
        });
    });
});