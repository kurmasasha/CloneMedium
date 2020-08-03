
function topicInCard(topic) {
    let tags = '';
    $.each(topic.hashtags, function (index, tag) {
        tags += '<a  href ="/topic/find/tag/'+ tag.name + '"> '+ tag.name +' </a>';
        if (index < (topic.hashtags.length - 1)) {
            tags += ', ';
        }
    })
    let authors = '';
    $.each(topic.authors, function (index, author) {
        authors += '<a href="/topic/find/author/' + author.id + '">' + author.firstName + " " + author.lastName + '</a>';
        if (index < (topic.authors.length - 1)) {
            authors += ', ';
        }
    })

    let themes = '';
    $.each(topic.themes, function (index, theme) {
        themes += '<a href="/topic/find/theme/' + theme.id + '">' + theme.name + '</a>';
        if (index < (topic.themes.length - 1)) {
            themes += ', ';
        }
    })

    let img = '';
    if (topic.img !== 'no-img.png') {
        img = '<img src="/topic-img/' + topic.img + '" alt="topic image">' +
                '<br> ' +
                '<br>'
    }

    let author_label = 'Автор: ';
    if (topic.authors.length > 1) {
        author_label = 'Авторы: ';
    }

    let moderated = 'notmoderated';
    if (topic.moderate) {
        moderated = 'moderated';
    }

    let active_like = '';

    let active_dis = '';

    getCountCommentsOfTopic(topic.id);

    let article = `
        <article class="topic topic_preview">
            <header class="topic_meta">
                <span class="author_name">` + author_label + authors + `</span>
                <span class="topic_time">` + topic.dateCreated + `</span>
            </header>
            <h2 class="topic_title">
                <a href="/topic/` + topic.id + `">` + topic.title + `</a>
            </h2>
            <h6 class="topic_theme">` + themes + `</h6>
            <h6 class="topic_tag">` + tags + `</h6>
            <div class="topic_body">
                <div class="topic_text">
                    ` + img + `
                    ` + linkify(topic.content) + ` 
                 </div>
            </div>
            <footer class="topic_footer">
                <ul class="topic_stats">
                    <li>
                        <div class="topic_stats_items ` + active_like + `">
                            <span class="fa fa-thumbs-o-up fa_topic" id="iconLikeOfTopic-${topic.id}"  data-id="${topic.id}">
                            </span>
                            <span class="text-info topic_stats_items_counter" id="likes" data-id="${topic.id}">
                                ${topic.likes}
                            </span>
                        </div>
                    </li>
                    <li>
                        <div class="topic_stats_items ` + active_dis + `">
                            <span class="fa fa-thumbs-o-down fa_topic" id="iconDislikeOfTopic-${topic.id}"  data-id="${topic.id}">
                            </span>
                            <span class="text-info topic_stats_items_counter" id="dislikes" data-id="${topic.id}">
                                ${topic.dislikes}
                            </span>
                        </div>
                    </li>
                    <li>
                        <div class="topic_stats_items topic_stats_items_comments">
                            <span class="fa fa-comments-o" data-id="${topic.id}"></span>
                            <span class="text-info topic_stats_items_counter" id="commentsCount-${topic.id}" data-id="${topic.id}"></span>
                        </div>
                    </li>
                </ul>
            </footer>
        </article>
    `

    return article;
}

async function getCountCommentsOfTopic(topicId) {
    const response = await fetch(`api/free-user/countCommentsOfTopic/${topicId}`);
    const count = await response.text();

    const commentCounterElement = document.querySelector(`#commentsCount-${topicId}`);
    commentCounterElement.textContent = count;
}


