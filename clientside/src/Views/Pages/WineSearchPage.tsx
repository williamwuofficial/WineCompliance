import * as React from 'react';
import { observer } from 'mobx-react';
import { Input, InputGroup, InputGroupText, ListGroup, ListGroupItem, } from 'reactstrap';
import { ReactComponent as SearchIcon } from '../../Assets/Icons/Search.svg';
import { ReactComponent as WineGlassIcon } from '../../Assets/Icons/WineGlass.svg';
import { ReactComponent as CrossIcon } from '../../Assets/Icons/Cross.svg';
import { action, makeObservable, observable, runInAction } from 'mobx';
import { RouteComponentProps } from 'react-router';
import WineEntity from '../../Model/WineEntity';
import '../../css/wine-search-page.css'

@observer
class WineSearchPage extends React.Component<RouteComponentProps> {
    constructor(props: any) {
        super(props);
        makeObservable(this);
    }

    @observable
    private searchInput: string = "";

    @observable
    private searchResults: WineEntity[] = [];

    @action
    private setSearchInput = async (e: any) => {
        this.searchInput = e.target.value;
        const response = await (await fetch('/api/breakdown/wine?searchInput=' + this.searchInput)).json();

        let serverResults: WineEntity[] = [];
        if (Array.isArray(response)) {
            for (const entity of response) {
                serverResults.push(new WineEntity(entity));
            }
        }

        runInAction(() => {
            this.searchResults = serverResults;
        });
    }

    @action
    private clearSearchInput = () => {
        this.searchInput = "";
        this.searchResults = [];
    }

    render() {
        return (
            <div className="wine-search-page h-100">
                <h3 className={'text-center font-weight-light'}>Wine Search <WineGlassIcon /></h3>
                <InputGroup>
                    <InputGroupText className='bg-white'>
                        <SearchIcon />
                    </InputGroupText>
                    <Input
                        className={'search-input'}
                        placeholder="Search by lot code and description ... "
                        value={this.searchInput}
                        onChange={this.setSearchInput} />
                    <InputGroupText 
                    className='bg-white' 
                    onClick={() => { this.clearSearchInput() }}>
                        <CrossIcon />
                    </InputGroupText>
                </InputGroup>
                <ListGroup>
                    {this.searchResults.map((wineEntity: WineEntity) =>
                        <ListGroupItem className={'d-flex justify-content-between'} key={wineEntity.lotCode} onClick={() => { this.props.history.push("/wine/" + wineEntity.lotCode) }}>
                            <div>
                                <div className='lot-code'>{wineEntity.lotCode}</div>
                                <div>{wineEntity.description ?? "No description"}</div>
                            </div>
                            <div>
                                <div>{wineEntity.volume.toLocaleString()} L</div>
                                <div>{wineEntity.tankCode}</div>
                            </div>
                        </ListGroupItem>
                    )}
                </ListGroup>
            </div>);
    }
}

export default WineSearchPage;