package Omer_Ran;

public class Doctor extends Lecturer implements Comparable <Doctor> {
    private String[] articles;
    public Doctor(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department, String[] articles) {
        super(name, id, degreeLevel, major, salary, department);
        this.articles = articles;
    }

    private String articlesDisplay() {
        sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < articles.length; i++) {
            if (articles[i] != null) {
                if (i == articles.length - 1) {
                    sb.append(articles[i]);
                } else {
                    sb.append(articles[i]).append(",");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return super.toString() + ", articles = " + articlesDisplay();
    }

    public String[] getArticles() {
        return articles;
    }

    @Override
    public int compareTo(Doctor o) {
        return Integer.compare(articles.length, o.articles.length);
    }
}
