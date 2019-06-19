package artifact.modules.common.entity;

public class Coffe implements Label {
    private Long id;
    private int label = ICE | MILK | SUGAR;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public String toString() {

        if ((this.label & Coffe.MILK) != 0) {
            System.out.println("加牛奶");
        }
        if ((this.label & Coffe.ICE) != 0) {
            System.out.println("加冰");
        }
        if ((this.label & Coffe.SUGAR) != 0) {
            System.out.println("加糖");
        }
        return super.toString();
    }


}

interface Label {
    int PURE = 0b0;
    int MILK = 0b1;
    int SUGAR = 0b10;
    int ICE = 0b100;
}

