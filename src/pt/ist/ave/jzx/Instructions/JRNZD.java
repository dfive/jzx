package pt.ist.ave.jzx.Instructions;


public class JRNZD extends Instruction {

	private int _tstates;
	
	public JRNZD(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		if (!_cpu.getM_zeroF()) {
			_tstates = 12;
			int m_pc16 = _cpu.getM_pc16();
			_cpu.setM_pc16(_cpu.add16(m_pc16, (byte) _cpu.getM_memory().read8(m_pc16) + 1));
		} else {
			_tstates = 7;
			_cpu.inc16pc();
		}
	}

	@Override
	public int incTstates() {
		return _tstates;
	}

}
