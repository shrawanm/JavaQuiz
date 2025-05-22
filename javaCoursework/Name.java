package javaCoursework;

public class Name {
	 Name() {
	    // Constructor implementation
	}

    private String name;
    public void setName(String name) {
        this.name = formatName(name);
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        String shortName = "";
        for (int i = 0; i < name.length(); i++) {
            if (Character.isUpperCase(name.charAt(i))) {
                shortName += name.charAt(i);
            }
        }
        return shortName;
    }

    String getFirstName() {
        String fname = "";
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                break;
            }
            fname += name.charAt(i);
        }
        return fname;
    }

    private String formatName(String name) {
        String[] words = name.toLowerCase().split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0))) 
                             .append(word.substring(1)) 
                             .append(" "); 
            }
        }
        return formattedName.toString().trim(); 
    }
}