$(document).ready(function (){
    const pathname = window.location.pathname;
    const segments = pathname.split('/');
    const postId = segments[2];
    const apiUrl = `/api/posts/${postId}`;
    const postApiUrl =`/api/answers/${postId}`;

    $.ajax( {
        url:apiUrl,
        method:"get",
        success: function (data) {
            $(".post-title").text(data.title);
            $(".data-type").text(data.type);
            $(".data-processType").text(data.processType);
            $(".data-size").text(data.size);
            $(".data-deadline").text(data.deadline);
            $(".data-contactMethod").text(data.contactMethod);
            $(".data-period").text(data.period);
            $(".data-position").html(data.position.map(pos => `<span>${pos}</span>`).join(' '));
            $(".data-techStack").html(data.techStack.map(tech => `<span>${tech}</span>`).join(' '));
            $(".post-description").text(data.description);
            $(".answer-list").html(data.answerList.map(answer => `<li>${answer.comment}</li>`));

        },
        error: function (error){
            console.log("에러",error);
        }
    });

    $(".btn").click(function (){
        const comment = $(".post-answer").val().trim();
        if (!comment){
            alert("댓글을 입력하세요.");
            return;
        }

        $.ajax({
            url: postApiUrl,
            method: "post",
            contentType: "application/json",
            data: JSON.stringify({comment: comment}),
            success: function (response){
                if (response){
                    $('.answer-list').prepend(`<li>${response.comment}</li>`)
                    $(".post-answer").val((""));
                }
            },
            error:function (error){
                console.log("response 에러",error);
            }

        });
    });
});