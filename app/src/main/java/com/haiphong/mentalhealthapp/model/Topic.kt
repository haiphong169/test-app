package com.haiphong.mentalhealthapp.model

data class Topic(
    val name: String,
    val postsList: List<Post> = listOf(),
    val articlesList: List<Article> = listOf()
)

data class Post(val title: String, val content: String, val topic: String)

val anxietyPosts = listOf(
    Post("What is anxiety", "To be added", "Anxiety"),
    Post("Why do we grow accustomed to anxiety", "To be added", "Anxiety"),
    Post("Different types of anxiety", "To be added", "Anxiety"),
    Post(
        "What are some reasons that we may overlook when it comes to anxiety",
        "To be added",
        "Anxiety"
    ),
    Post("How to deal with anxiety", "To be added", "Anxiety")
)

val anxietyArticles = listOf(
    Article(
        title = "Anxiety: Symptoms, types, causes, prevention, and treatment",
        content = "Anxiety is a natural emotion . However, it can also cause physical symptoms, such as shaking and sweating.Anxiety disorders can affect daily life and can improve with treatment.Anxiety disorders form a category of mental health diagnoses that lead to excessive nervousness, fear, apprehension, and worry . This article discusses the symptoms, causes, and treatments for anxiety.According to the Anxiety and Depression Association of America( ADAA), around40 million people in the United States have an anxiety disorder.It is the most common group of mental illnesses in the country.However, only36.9 % of people with an anxiety disorder receive treatment .",
        url = "https://www.medicalnewstoday.com/articles/323454"
    ),
    Article(
        title = "Understanding Anxiety Disorders",
        content = " A monthly newsletter from the National Institutes of Health, part of the U . S . Department of Health and Human Services March 2016 Print this issue When Panic, Fear, and Worries Overwhelm",
        url = "https://newsinhealth.nih.gov/2016/03/understanding-anxiety-disorders"
    ),
    Article
        (
        title = "Anxiety - StatPearls",
        content = "An official website of the United States government",
        url = "https://www.ncbi.nlm.nih.gov/books/NBK470361/"
    ),
    Article
        (
        title = "Anxiety: What it is, what to do",
        content = "FDA approves new surgical treatment for enlarged prostates Kidneys, eyes, ears, and more : Why do we have a spare ?",
        url = "https://www.health.harvard.edu/blog/anxiety-what-it-is-what-to-do-2018060113955"
    ),
    Article
        (
        title = "Anxiety",
        content = "",
        url = "https://www.psychologytoday.com/us/basics/anxiety"
    ),
    Article
        (
        title = "When Does Anxiety Become a Problem ?",
        content = "ADVERTISEMENT The president of the American Psychiatric Association answers questions about a new recommendation to screen all adults under65for anxiety.",
        url = "https://www.nytimes.com/2023/06/23/well/mind/anxiety-screening-symptoms-treatment.html"
    )
)

val topics =
    listOf(
        Topic("Anxiety", anxietyPosts, anxietyArticles),
        Topic("Depression"),
        Topic("Eating Disorder"),
        Topic("Addiction"),
        Topic("Stress")
    )



