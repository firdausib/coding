public class MatrixAksi {
    private final int M;             // number of rows
    private final int N;             // number of columns
    private final double[][] data;   // M-by-N array
    
    public MatrixAksi(int M, int N){
        this.M = M;
    this.N = N;
    data = new double[M][N];
    }
    // create matrix based on 2d array
public MatrixAksi(double[][] data) {
    M = data.length;
    N = data[0].length;
    this.data = new double[M][N];
    for (int i = 0; i < M; i++)
        for (int j = 0; j < N; j++)
                this.data[i][j] = data[i][j];
}

// copy constructor
private MatrixAksi(MatrixAksi A) { this(A.data); }

// create and return a random M-by-N matrix with values between 0 and 1
public static MatrixAksi random(int M, int N) {
    MatrixAksi A = new MatrixAksi(M, N);
    for (int i = 0; i < M; i++)
        for (int j = 0; j < N; j++)
            A.data[i][j] = Math.random();
    return A;
}

// create and return the N-by-N identity matrix
public static MatrixAksi identity(int N) {
    MatrixAksi I = new MatrixAksi(N, N);
    for (int i = 0; i < N; i++)
        I.data[i][i] = 1;
    return I;
}

// swap rows i and j
void swap(int i, int j) {
    double[] temp = data[i];
    data[i] = data[j];
    data[j] = temp;
}

// create and return the transpose of the invoking matrix
public MatrixAksi transpose() {
    MatrixAksi A = new MatrixAksi(N, M);
    for (int i = 0; i < M; i++)
        for (int j = 0; j < N; j++)
            A.data[j][i] = this.data[i][j];
    return A;
}

// return C = A + B
public MatrixAksi plus(MatrixAksi B) {
    MatrixAksi A = this;
    if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
    MatrixAksi C = new MatrixAksi(M, N);
    for (int i = 0; i < M; i++)
        for (int j = 0; j < N; j++)
            C.data[i][j] = A.data[i][j] + B.data[i][j];
    return C;
}


// return C = A - B
public MatrixAksi minus(MatrixAksi B) {
    MatrixAksi A = this;
    if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
    MatrixAksi C = new MatrixAksi(M, N);
    for (int i = 0; i < M; i++)
        for (int j = 0; j < N; j++)
            C.data[i][j] = A.data[i][j] - B.data[i][j];
    return C;
}

// does A = B exactly?
public boolean eq(MatrixAksi B) {
    MatrixAksi A = this;
    if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
    for (int i = 0; i < M; i++)
        for (int j = 0; j < N; j++)
            if (A.data[i][j] != B.data[i][j]) return false;
    return true;
}

// return C = A * B
public MatrixAksi times(MatrixAksi B) {
    MatrixAksi A = this;
    if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
    MatrixAksi C = new MatrixAksi(A.M, B.N);
    for (int i = 0; i < C.M; i++)
        for (int j = 0; j < C.N; j++)
            for (int k = 0; k < A.N; k++)
                C.data[i][j] += (A.data[i][k] * B.data[k][j]);
    return C;
}


// return x = A^-1 b, assuming A is square and has full rank
public MatrixAksi solve(MatrixAksi rhs) {
    if (M != N || rhs.M != N || rhs.N != 1)
        throw new RuntimeException("Illegal matrix dimensions.");

    // create copies of the data
    MatrixAksi A = new MatrixAksi(this);
    MatrixAksi b = new MatrixAksi(rhs);

    // Gaussian elimination with partial pivoting
    for (int i = 0; i < N; i++) {

        // find pivot row and swap
        int max = i;
        for (int j = i + 1; j < N; j++)
            if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
                max = j;
        A.swap(i, max);
        b.swap(i, max);

        // singular
        if (A.data[i][i] == 0.0) throw new RuntimeException("Matrix is singular.");

        // pivot within b
        for (int j = i + 1; j < N; j++)
            b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];

        // pivot within A
        for (int j = i + 1; j < N; j++) {
            double m = A.data[j][i] / A.data[i][i];
            for (int k = i+1; k < N; k++) {
                A.data[j][k] -= A.data[i][k] * m;
            }
            A.data[j][i] = 0.0;
        }
    }

    // back substitution
    MatrixAksi x = new MatrixAksi(N, 1);
    for (int j = N - 1; j >= 0; j--) {
        double t = 0.0;
        for (int k = j + 1; k < N; k++)
            t += A.data[j][k] * x.data[k][0];
        x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
    }
    return x;

}

// print matrix to standard output
public void show() {
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) 
            System.out.printf("%9.4f ", data[i][j]);
            System.out.println();
    }
}
        // a. Membuat Matrix A
    public static void main(String[] args){
        double[][] d = { { 1, 2, 3 }, { 4, 5, 6 }, { 9, 1, 3} };
        MatrixAksi D = new MatrixAksi(d);
        D.show();       
        System.out.println("\n");
        // a. Matrix random A
        System.out.println("a) Matrix A Random MxN");
        MatrixAksi A = MatrixAksi.random(3, 3);
        A.show(); 
        System.out.println("\n");
        // b. Transpose Matrix A(B)
        System.out.println("b) Transpose dari Matrix A (B)");
        A.swap(1, 1);
        A.show(); 
        System.out.println("\n");
        System.out.println("Hasil Transpose : ");
        MatrixAksi B = A.transpose();
        B.show(); 
        System.out.println("\n");
        // c. Matrix identitas (C)
        System.out.println("c) Matrix Identitas C ");
        MatrixAksi C = MatrixAksi.identity(3);
        C.show(); 
        System.out.println("\n");
        // d. Matrix A + B
        System.out.println("d) Matrix A + B");
        A.plus(B).show();
        System.out.println("\n");
        // e. Matrix A x B
        System.out.println("e) Matrix A * B");
        B.times(A).show();
        System.out.println("\n");
        // shouldn't be equal since AB != BA in general
        System.out.println("shouldn't be equal since AB != BA in general");
        System.out.println(A.times(B).eq(B.times(A)));
        System.out.println("\n");
       
    }   
}