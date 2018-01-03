
package si.fri.rso.upravljanjestrani;

import java.util.ArrayList;
import java.util.List;


public class Database {
    private static List<Stran> strans = new ArrayList<>();

    public static List<Stran> getStrans() {
        /*Stran cus = new Stran();
        cus.setId("1");
        cus.setFirstName("Janez");
        cus.setLastName("Novak");
        strans.add(cus);*/

        return strans;
    }

    public static Stran getStran(String stranId) {
        for (Stran stran : strans) {
            if (stran.getId().equals(stranId))
                return stran;
        }

        return null;
    }

    public static void addStran(Stran stran) {
        strans.add(stran);
    }

    public static void deleteStran(String stranId) {
        for (Stran stran : strans) {
            if (stran.getId().equals(stranId)) {
                strans.remove(stran);
                break;
            }
        }
    }
}
