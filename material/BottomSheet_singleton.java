1. Definición del BottomSheetDialogFragment

public class RecetasBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_RECETA_ID = "receta_id";
    private RecetarioViewModel viewModel;
    private Receta recetaSeleccionada;

    public RecetasBottomSheet() {
        // Constructor vacío obligatorio
    }

    // Método de fábrica para crear el fragment y pasar argumentos
    public static RecetasBottomSheet newInstance(int recetaId) {
        RecetasBottomSheet fragment = new RecetasBottomSheet();
        Bundle args = new Bundle();
        args.putInt(ARG_RECETA_ID, recetaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.ThemeBottomSheet);

        viewModel = new ViewModelProvider(requireActivity()).get(RecetarioViewModel.class);

        // Recuperar el argumento
        if (getArguments() != null) {
            int recetaId = getArguments().getInt(ARG_RECETA_ID);
            // Recuperar la receta desde el ViewModel (por id, por ejemplo)
            recetaSeleccionada = viewModel.getRecetaPorId(recetaId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RecetasBottomSheetBinding binding =
                RecetasBottomSheetBinding.inflate(inflater, container, false);

        binding.opcionVer.setOnClickListener(v ->
                listener.onClickVer(recetaSeleccionada));

        binding.opcionEditar.setOnClickListener(v ->
                listener.onClickEditar(recetaSeleccionada));

        binding.opcionEliminar.setOnClickListener(v ->
                listener.onClickEliminar(recetaSeleccionada));

        return binding.getRoot();
    }

    // Interfaz para comunicar las acciones
    public interface OnClickOpcionListener {
        void onClickVer(Receta receta);
        void onClickEditar(Receta receta);
        void onClickEliminar(Receta receta);
    }

    private OnClickOpcionListener listener;

    public void setOnClickOpcionListener(OnClickOpcionListener listener) {
        this.listener = listener;
    }
}

2. En RecetasActivity:

RecetasBottomSheet bottomSheet = RecetasBottomSheet.newInstance(receta.getId());

bottomSheet.setOnClickOpcionListener(new RecetasBottomSheet.OnClickOpcionListener() {
    @Override
    public void onClickVer(Receta receta) {
        // Abrir detalle
    }

    @Override
    public void onClickEditar(Receta receta) {
        // Ir a la actividad de edición
    }

    @Override
    public void onClickEliminar(Receta receta) {
        // Confirmar y eliminar
    }
});

bottomSheet.show(getSupportFragmentManager(), "RecetasBottomSheet");