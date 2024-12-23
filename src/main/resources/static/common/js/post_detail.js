$(document).ready(function (){
    const pathname = window.location.pathname;
    const segments = pathname.split('/');
    const postId = segments[2];
    const apiUrl = `/api/posts/${postId}`;

    $.ajax( {
        url:apiUrl,
        method:"get",
        success: function (data) {
            console.log("성공",data);
            const postTitle = $(".post-title");
            const postDescription = $(".post-description");
            postTitle.text(data.title);
            $(".data-type").text(data.type);
            $(".data-processType").text(data.processType);
            $(".data-size").text(data.size);
            $(".data-deadline").text(data.deadline);
            $(".data-contactMethod").text(data.contactMethod);
            $(".data-period").text(data.period);
            $(".data-position").html(data.position.map(pos => `<span>${pos}</span>`).join(' '));
            $(".data-techStack").html(data.techStack.map(tech => `<span>${tech}</span>`).join(' '));
            postDescription.text(data.description);
        },
        error: function (error){
            console.log("에러",error);
        }
    });
});