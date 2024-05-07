import java.util.PriorityQueue;
import java.util.HashMap;

class HuffmanNode implements Comparable<HuffmanNode> {
    char data;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        left = right = null;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.frequency - node.frequency;
    }
}

public class HuffmanCoding {
    public static HashMap<Character, String> generateHuffmanCodes(String text) {
        HashMap<Character, String> huffmanCodes = new HashMap<>();
        HashMap<Character, Integer> frequencies = new HashMap<>();

        // Count frequency of each character
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }

        // Create a priority queue to store nodes
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (char c : frequencies.keySet()) {
            pq.add(new HuffmanNode(c, frequencies.get(c)));
        }

        // Build Huffman tree
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            pq.add(parent);
        }

        // Traverse the Huffman tree to generate codes
        traverseHuffmanTree(pq.poll(), "", huffmanCodes);

        return huffmanCodes;
    }

    private static void traverseHuffmanTree(HuffmanNode root, String code, HashMap<Character, String> huffmanCodes) {
        if (root == null) {
            return;
        }
        if (root.data != '\0') {
            huffmanCodes.put(root.data, code);
        }
        traverseHuffmanTree(root.left, code + "0", huffmanCodes);
        traverseHuffmanTree(root.right, code + "1", huffmanCodes);
    }

    public static void main(String[] args) {
        String text = "tengo 6 perros y 4 gatos. 3 perros son negros";
        HashMap<Character, String> huffmanCodes = generateHuffmanCodes(text);

        System.out.println("Huffman Codes:");
        for (char c : huffmanCodes.keySet()) {
            System.out.println(c + ": " + huffmanCodes.get(c));
        }
    }
}