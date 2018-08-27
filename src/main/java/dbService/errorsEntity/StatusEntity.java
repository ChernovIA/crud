package dbService.errorsEntity;

public class StatusEntity<T> {
    private String desc;
    private DBStatusResults statusResults;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public StatusEntity(){
        this.desc = "";
        this.statusResults = DBStatusResults.OK;
    }
    public StatusEntity(String desc, DBStatusResults statusResults){
        this.desc = desc;
        this.statusResults = statusResults;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DBStatusResults getStatusResults() {
        return statusResults;
    }

    public void setStatusResults(DBStatusResults statusResults) {
        this.statusResults = statusResults;
    }

    public String getDesc() {

        return desc;
    }

    public void setError(Exception e) {
        setDesc(e.toString());
        setStatusResults(DBStatusResults.ERROR);
    }
}
