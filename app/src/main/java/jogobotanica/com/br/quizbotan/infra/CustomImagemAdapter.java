package jogobotanica.com.br.quizbotan.infra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import jogobotanica.com.br.quizbotan.R;
import jogobotanica.com.br.quizbotan.dominio.Ranking;

public class CustomImagemAdapter extends BaseAdapter {

    private Context context;
    private List<Ranking> listaRanking;

    public CustomImagemAdapter(Context context, List<Ranking> listaRanking) {
        this.context = context;
        this.listaRanking = listaRanking;
    }

    @Override
    public int getCount() {
        return listaRanking.size();
    }

    @Override
    public Object getItem(int position) {
        return listaRanking.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.linhaspontos,null);

        ImageView imagemTopo = (ImageView)view.findViewById(R.id.imagemTopo);
        TextView textViewTopo = (TextView)view.findViewById(R.id.textViewTopo);

        if(position == 0 ) {
            imagemTopo.setImageResource(R.drawable.imagem_top1);
        }else if(position == 1) {
            imagemTopo.setImageResource(R.drawable.imagem_top2);
        }else {
            imagemTopo.setImageResource(R.drawable.imagem_top3);
        }
        textViewTopo.setText(String.format("%.1f", listaRanking.get(position).getScore()));
        return view;
    }
}
