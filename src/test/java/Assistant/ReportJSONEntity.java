package Assistant;

import java.util.List;

public class ReportJSONEntity {

    public String name;
    public String status;
    public String description;
    public StatusDetails statusDetails;
    public List<Labels> labels;

    public class StatusDetails{
        public String message;
        public String trace;
    }

    public class Labels{
        public String value;
    }

}
