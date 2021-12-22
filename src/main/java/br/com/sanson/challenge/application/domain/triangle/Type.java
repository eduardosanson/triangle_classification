package br.com.sanson.challenge.application.domain.triangle;

public enum Type {
    EQUILATERAL{
        @Override
        public boolean match(Integer first, Integer second, Integer third) {
            return first == second && second == third;
        }
    },
    ISOSCELES{
        @Override
        public boolean match(Integer first, Integer second, Integer third) {
            return first==second || first==third || second==third;
        }
    },
    SCALENE{
        @Override
        public boolean match(Integer first, Integer second, Integer third) {
            return false;
        }
    };

    public abstract boolean match(Integer first, Integer second, Integer third);
}
